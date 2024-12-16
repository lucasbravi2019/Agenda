import React, { useCallback, useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { selectActualMonth, selectActualYear, selectModalTasks, selectTasks } from './util/selectors';
import { runGetTasks, setActualMonth, setActualYear, setModalTasks, setSelectedDate } from './util/task-slice';
import { Button, Calendar, Modal } from 'antd';
import ListComponent from '../../components/list';
import EventForm from '../../components/form/event-form';

const Home = () => {
    const dispatch = useDispatch();
    const allTasks = useSelector(selectTasks);
    const modalTasks = useSelector(selectModalTasks);
    const actualYear = useSelector(selectActualYear)
    const actualMonth = useSelector(selectActualMonth)
    const [open, setOpen] = useState(false);

    useEffect(() => {
        dispatch(runGetTasks())
    }, [dispatch])

    const dateCellRender = (current) => {
        const tasks = getEventsByDay(current.year(), current.month(), current.date(), allTasks);

        if (tasks.length === 0)
            return null

        return (
            <ListComponent
                data={tasks}
            />
        )
    }

    const getEventsByDay = useCallback((year, month, day, tasks) => {
        if (tasks[year] && tasks[year][month] && tasks[year][month][day])
            
            return [...tasks[year][month][day].map(event => event.eventDescription)]

        return []
    }, [])

    const getModalEvents = useCallback((year, month, day, tasks) => {
        if (tasks[year] && tasks[year][month] && tasks[year][month][day])
            
            return [...tasks[year][month][day]]

        return []
    }, [])

    const cellRender = (current, info) => {
        if (info.type === 'date') {
            if (info.today.year() !== actualYear)
                dispatch(setActualYear(info.today.year()))
            if (info.today.month() !== actualMonth)
                dispatch(setActualMonth(info.today.month()))

            return dateCellRender(current);
        }
        return info.originNode;
    }

    const handleCancel = useCallback(() => {
        setOpen(false);
    }, [])

    const onSelect = (newValue, info) => {
        if (info.source !== 'date')
            return
        
        setOpen(true);
        const tasks = getModalEvents(newValue.year(), newValue.month(), newValue.date(), allTasks);
        console.log(tasks);
        
        const selectedDate = { year: newValue.year(), month: newValue.month(), day: newValue.date() }
        dispatch(setSelectedDate(selectedDate))
        dispatch(setModalTasks(tasks));
    }

    return (
        <div>
            <h1>Agenda</h1>
            <Calendar cellRender={cellRender} onSelect={onSelect} />
            <Modal
                title="Events"
                open={open}
                onCancel={handleCancel}
                footer={<Button onClick={handleCancel}>Cancel</Button>}
            >
                <EventForm data={modalTasks} closeModal={handleCancel} />
            </Modal>
        </div>
    )
}

export default Home