package ru.yourlovedpolish.jwtapp.rest;


import javassist.compiler.ast.Pair;
import lombok.extern.slf4j.Slf4j;
import net.fortuna.ical4j.model.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yourlovedpolish.jwtapp.ICSWriter;
import ru.yourlovedpolish.jwtapp.dto.FindScheduleByIdBetweenDateRequestDto;
import ru.yourlovedpolish.jwtapp.dto.FindScheduleRequestDto;
import ru.yourlovedpolish.jwtapp.model.Group;
import ru.yourlovedpolish.jwtapp.model.PairType;
import ru.yourlovedpolish.jwtapp.model.ScheduleItem;
import ru.yourlovedpolish.jwtapp.service.GroupService;
import ru.yourlovedpolish.jwtapp.service.PairTypeService;
import ru.yourlovedpolish.jwtapp.service.ScheduleItemService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/schedule/")
public class ScheduleRestController {
    private final GroupService groupService;
    private final ScheduleItemService scheduleItemService;
    private final PairTypeService pairTypeService;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final ICSWriter icsWriter = new ICSWriter("src/main/resources/schedule.ics");
    @Autowired
    public ScheduleRestController(GroupService groupService, ScheduleItemService scheduleItemService, PairTypeService pairTypeService) {
        this.groupService = groupService;
        this.scheduleItemService = scheduleItemService;
        this.pairTypeService = pairTypeService;
    }

    @GetMapping("get_groups")
    public ResponseEntity<List<Group>> getAllGroups()
    {
        log.info("Getting all groups");
        List<Group> result = groupService.getAll();

        return result != null
                ?new ResponseEntity<>(result, HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("get_all_types")
    public ResponseEntity<List<PairType>> getAllPairTypes()
    {
        log.info("Getting all pair types");
        List<PairType> result = pairTypeService.getAll();

        return result != null
                ?new ResponseEntity<>(result, HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("get_schedule_by_id")
    public ResponseEntity<Optional<ScheduleItem>> getScheduleById(@RequestBody FindScheduleRequestDto dto)
    {
        log.info("Getting all groups");
        Optional<ScheduleItem> result = scheduleItemService.findById(dto.getId());

        return !result.isEmpty()
                ?new ResponseEntity<>(result, HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("get_schedule_by_id_between_date")
    public ResponseEntity<List<ScheduleItem>> getScheduleByIdBetweenDate(@RequestBody FindScheduleByIdBetweenDateRequestDto dto)
    {
        log.info("Getting all groups");
        List<ScheduleItem> result = scheduleItemService.findByIdGroupBetweenDate(dto.getId(), dto.getStart(), dto.getEnd());

        return !result.isEmpty()
                ?new ResponseEntity<>(result, HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @GetMapping("get_schedule")
    public ResponseEntity<Object> downloadFile(HttpServletRequest request) throws IOException
    {
        Long groupId = Long.parseLong(request.getParameter("id"));
        Date start = null, end = null;
        try {
            start = dateFormat.parse(request.getParameter("start"));
            end = dateFormat.parse(request.getParameter("end"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("Get id: {} {} {} ", groupId, dateFormat.format(start), dateFormat.format(end));
        List<ScheduleItem> result = scheduleItemService.findByIdGroupBetweenDate(groupId, start, end);
        try {
            icsWriter.writeISCFile(result);
            log.info("Жопа на месте");
        } catch (ValidationException e) {
            log.info("Отвалилась жопа");
        }
        String filename = "src/main/resources/schedule.ics";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        return responseEntity;
    }

}
