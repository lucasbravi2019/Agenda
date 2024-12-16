package com.bravi.agenda.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@Configuration
public class NativeQueryBean {

    private static final String NAME = "name";
    private static final String QUERY = "query";
    private static final String DIRECTORY = "classpath:queries/*.xml";
    private final ResourcePatternResolver pathMatchingResourcePatternResolver;

    public NativeQueryBean() {
        this.pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    }

    @Bean(name = "nativeQueries")
    public Properties getNativeQueries() {
        Resource[] resources = loadQueriesFiles();

        return loadQueries(resources);
    }

    private Resource[] loadQueriesFiles() {
        try {
            return pathMatchingResourcePatternResolver.getResources(DIRECTORY);
        } catch (IOException ex) {
            log.error("Error al intentar cargar " + DIRECTORY + ": {}", ex.getMessage(), ex);
            throw new IllegalStateException("Error al cargar queries", ex);
        }
    }

    private Properties loadQueries(Resource[] resources) {
        Properties queriesStorage = new Properties();
        try {
            for (Resource resource : resources) {
                if (resource.exists()) {
                    log.info("Trying to deserialize file: {}", resource.getFilename());
                    try (InputStream in = resource.getInputStream()) {
                        parseXML(in, queriesStorage);
                    }
                }
            }
            return queriesStorage;
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            log.error("Error al cargar archivo: {}", ex.getMessage(), ex);
            throw new IllegalStateException("Error al cargar queries", ex);
        }
    }

    private void parseXML(InputStream in, Properties queriesStorage) throws ParserConfigurationException, IOException, SAXException {
        log.info("Intentando parsear xml: {}", in);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(in);

        NodeList nodeList = document.getElementsByTagName(QUERY);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);

            String name = element.getAttribute(NAME);
            String query = element.getTextContent().trim();
            queriesStorage.put(name, query);
        }

    }

}
