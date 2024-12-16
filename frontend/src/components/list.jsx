import { List } from "antd"

const ListComponent = (props) => {
    const { data } = props;

    return (
        <List
            size="small"
            dataSource={data}
            renderItem={(item) => <List.Item>{item}</List.Item>}
        />
    )
}

export default ListComponent;