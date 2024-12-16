import { Button, Form, Input, Space } from "antd"
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { runPatchTasks } from "../../pages/home/util/task-slice";

const EventForm = (props) => {
    const dispatch = useDispatch()
    const { data, closeModal } = props
    const [form] = Form.useForm()

    const handleFinish = (values) => {
        console.log(values);
        
        dispatch(runPatchTasks(values))
        closeModal()
    }

    useEffect(() => {
        if (form)
            form.resetFields()
    }, [data, form])

    const getInitialValues = () => {
        console.log(data);
        
        return {
            events: [...data.map(event => {
                return {
                    id: event.id,
                    description: event.eventDescription
                }
            })]
        }
    }

    return (
        <Form
            form={form}
            name="event-form"
            onFinish={handleFinish}
            autoComplete="off"
            initialValues={getInitialValues()}
        >
            <Form.List name="events">
                {(fields, { add, remove }) => (
                    <>
                        {fields.map(({ key, name, ...restField }) => (
                            <Space
                                style={{
                                    display: 'grid',
                                    width: '100%',
                                    gridTemplateColumns: '0% 80% 20%',
                                    marginBottom: 0,
                                }}
                                align="baseline"
                                key={key}
                            >
                                <Form.Item {...restField}
                                    name={[name, 'id']}
                                    hidden="true"
                                >
                                    <Input style={{
                                        display: 'none'
                                    }} />
                                </Form.Item>
                                <Form.Item {...restField}
                                    name={[name, 'description']}
                                    rules={[
                                        {
                                            required: true, message: 'Event is required'
                                        }
                                    ]}
                                >
                                    <Input placeholder="Event" style={{
                                        display: 'block'
                                    }} />
                                </Form.Item>
                                <MinusCircleOutlined onClick={() => remove(name)} />
                            </Space>
                        ))}
                        <Form.Item >
                            <Button type="dashed" onClick={() => add()} block icon={<PlusOutlined />}>Add Event</Button>
                        </Form.Item>
                    </>
                )}
            </Form.List>
            <Form.Item >
                <Button type="primary" htmlType="submit">Save</Button>
            </Form.Item>
        </Form>
    )
}

export default EventForm