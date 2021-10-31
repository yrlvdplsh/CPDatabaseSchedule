package ru.yourlovedpolish.jwtapp;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;
import ru.yourlovedpolish.jwtapp.model.ScheduleItem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.GregorianCalendar;
import java.util.List;

public class ICSWriter {
    private final String filename;

    public ICSWriter(String filename) {
        this.filename = filename;
    }
    public void writeISCFile(List<ScheduleItem> items) throws IOException, ValidationException {
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("Europe/Moscow");
        VTimeZone tz = timezone.getVTimeZone();
        Calendar icsCalendar = new Calendar();
        icsCalendar.getProperties().add(new ProdId("-//ylp-schedule"));
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        for (ScheduleItem item: items) {
            java.util.Calendar startDate = new GregorianCalendar();
            startDate.setTimeZone(timezone);
            startDate.set(java.util.Calendar.MONTH, item.getDate().getMonth());
            startDate.set(java.util.Calendar.DAY_OF_MONTH, item.getDate().getDay());
            startDate.set(java.util.Calendar.YEAR, item.getDate().getYear());
            startDate.set(java.util.Calendar.HOUR_OF_DAY, item.getDate().getHours());
            startDate.set(java.util.Calendar.MINUTE, item.getDate().getMinutes());
            startDate.set(java.util.Calendar.SECOND, item.getDate().getSeconds());
            // Конец в этот же день в 20:00
            java.util.Date itemEndDate = item.getDate();
            itemEndDate.setTime(itemEndDate.getTime() + 5400000);
            java.util.Calendar endDate = new GregorianCalendar();
            endDate.setTimeZone(timezone);
            endDate.set(java.util.Calendar.MONTH, itemEndDate.getMonth());
            endDate.set(java.util.Calendar.DAY_OF_MONTH, itemEndDate.getDay());
            endDate.set(java.util.Calendar.YEAR, itemEndDate.getYear());
            endDate.set(java.util.Calendar.HOUR_OF_DAY, itemEndDate.getHours());
            endDate.set(java.util.Calendar.MINUTE, itemEndDate.getMinutes());
            endDate.set(java.util.Calendar.SECOND, itemEndDate.getSeconds());
// Создаем событие
            String eventName = item.getSubject().getName();
            DateTime start = new DateTime(startDate.getTime());
            DateTime end = new DateTime(endDate.getTime());
            VEvent meeting = new VEvent(start, end, eventName);
// Добавляем к нему информацию о часовом поясе
            meeting.getProperties().add(tz.getTimeZoneId());
            UidGenerator ug = new UidGenerator("123");
            Uid uid = ug.generateUid();
            Description description = new Description(item.getPairType().getType().toUpperCase().toString()
                                    + " \n" + item.getTutor() !=null?item.getTutor().getName() + " \n":""+
                                    item.getAuditorium()!=null?item.getAuditorium().getAddress():"");
            meeting.getProperties().add(uid);
            meeting.getProperties().add(description);
// Добавляем к нему созданное событие
            icsCalendar.getComponents().add(meeting);
        }
        FileOutputStream fout = new FileOutputStream(filename);
        CalendarOutputter out = new CalendarOutputter();
        out.output(icsCalendar, fout);
    }
}
