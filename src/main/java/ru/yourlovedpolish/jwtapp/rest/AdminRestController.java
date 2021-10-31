package ru.yourlovedpolish.jwtapp.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yourlovedpolish.jwtapp.dto.AddScheduleItemDto;
import ru.yourlovedpolish.jwtapp.model.*;
import ru.yourlovedpolish.jwtapp.security.jwt.JwtTokenProvider;
import ru.yourlovedpolish.jwtapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/admin/")
public class AdminRestController
{
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final TutorService tutorService;
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final AuditoriumService auditoriumService;
    private final PairTypeService pairTypeService;
    private final ScheduleItemService scheduleItemService;
    @Autowired
    public AdminRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, TutorService tutorService, GroupService groupService, SubjectService subjectService, AuditoriumService auditoriumService, PairTypeService pairTypeService, ScheduleItemService scheduleItemService)
    {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.tutorService = tutorService;
        this.groupService = groupService;
        this.subjectService = subjectService;
        this.auditoriumService = auditoriumService;
        this.pairTypeService = pairTypeService;
        this.scheduleItemService = scheduleItemService;
    }

    @GetMapping("get_all_groups")
    public ResponseEntity<List<Group>> getAllGroups()
    {
        log.info("Getting all groups");
        List<Group> result = groupService.getAll();
        return result != null
                ?new ResponseEntity<>(result, HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("delete_group")
    public ResponseEntity<Object> deleteGroupById(HttpServletRequest request)
    {
        Long groupId = Long.parseLong(request.getParameter("id"));
        log.info("Deleting {} group", groupId);

        try
        {
            groupService.deleteById(groupId);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Group with id {} deleted", groupId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("get_all_tutors")
    public ResponseEntity<List<Tutor>> getAllTutors()
    {
        log.info("Getting all tutors");
        List<Tutor> result = tutorService.getAll();
        return result != null
                ?new ResponseEntity<>(result, HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("delete_tutor")
    public ResponseEntity<Object> deleteTutorById(HttpServletRequest request)
    {
        Long tutorId = Long.parseLong(request.getParameter("id"));
        log.info("Deleting {} tutor", tutorId);
        try
        {
            tutorService.deleteById(tutorId);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Tutor with id {} deleted", tutorId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("get_all_subjects")
    public ResponseEntity<List<Subject>> getAllSubjects()
    {
        log.info("Getting all subjects");
        List<Subject> result = subjectService.getAll();
        return result != null
                ?new ResponseEntity<>(result, HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("delete_subject")
    public ResponseEntity<Object> deleteSubjectById(HttpServletRequest request)
    {
        Long subjectId = Long.parseLong(request.getParameter("id"));
        log.info("Deleting {} subject", subjectId);
        try
        {
            subjectService.deleteById(subjectId);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Subject with id {} deleted", subjectId);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @GetMapping("delete_schedule")
    public ResponseEntity<Object> deleteScheduleById(HttpServletRequest request)
    {
        Long scheduleId = Long.parseLong(request.getParameter("id"));
        log.info("Deleting {} subject", scheduleId);
        try
        {
            scheduleItemService.deleteById(scheduleId);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Subject with id {} deleted", scheduleId);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @GetMapping("get_all_auditoriums")
    public ResponseEntity<List<Auditorium>> getAllAuditoriums()
    {
        log.info("Getting all subjects");
        List<Auditorium> result = auditoriumService.getAll();
        return result != null
                ?new ResponseEntity<>(result, HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("delete_auditorium")
    public ResponseEntity<Object> deleteAuditoriumById(HttpServletRequest request)
    {
        Long auditoriumId = Long.parseLong(request.getParameter("id"));
        log.info("Deleting {} auditorium", auditoriumId);
        try
        {
            auditoriumService.deleteById(auditoriumId);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Auditorium with id {} deleted", auditoriumId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("add_scheduleitem")
    public Object addScheduleItem(HttpServletRequest request)
    {
        ScheduleItem item = new ScheduleItem();
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            item.setGroup(groupService.findById(Long.parseLong(request.getParameter("groupId"))).get());
            item.setAuditorium(auditoriumService.findById(Long.parseLong(request.getParameter("auditoriumId"))).get());
            item.setPairType(pairTypeService.findById(Long.parseLong(request.getParameter("pairTypeId"))).get());
            item.setTutor(tutorService.findById(Long.parseLong(request.getParameter("tutorId"))).get());
            item.setSubject(subjectService.findById(Long.parseLong(request.getParameter("subjectId"))).get());
            item.setDate(formatter.parse(request.getParameter("date")));
            switch (Integer.parseInt(request.getParameter("pairNumber")))
            {
                case 1:
                {
                    item.getDate().setHours(9);
                    item.getDate().setMinutes(0);
                    break;
                }
                case 2:
                {
                    item.getDate().setHours(10);
                    item.getDate().setMinutes(45);
                    break;
                }
                case 3:
                {
                    item.getDate().setHours(13);
                    item.getDate().setMinutes(0);
                    break;
                }
                case 4:
                {
                    item.getDate().setHours(14);
                    item.getDate().setMinutes(45);
                    break;
                }
                case 5:
                {
                    item.getDate().setHours(16);
                    item.getDate().setMinutes(30);
                    break;
                }
                case 6:
                {
                    item.getDate().setHours(18);
                    item.getDate().setMinutes(15);
                    break;
                }
            }

            scheduleItemService.add(item);
            return "OK";
        } catch (Exception e)
        {
            log.info("Can't add schedule item");
            return "NO";
        }

    }


/*
    @PostMapping("add_scheduleitem")
    public Object addScheduleItem(@RequestBody AddScheduleItemDto dto)
    {
        ScheduleItem item = new ScheduleItem();
        try
        {
            item.setGroup(groupService.findById(dto.getGroupId()).get());
            item.setAuditorium(auditoriumService.findById(dto.getAuditoriumId()).get());
            item.setPairType(pairTypeService.findById(dto.getPairTypeId()).get());
            item.setTutor(tutorService.findById(dto.getTutorId()).get());
            item.setSubject(subjectService.findById(dto.getSubjectId()).get());
            item.setDate(dto.getDate());
            scheduleItemService.add(item);
            return "OK";
        } catch (Exception e)
        {
            log.info("Can't add schedule item");
            return "NO";
        }

    }
*/


    @GetMapping("add_group")
    public ResponseEntity<Object> addGroup(HttpServletRequest request)
    {
        Group group = new Group();
        group.setName(request.getParameter("name"));
        try
        {
            groupService.add(group);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e)
        {
            log.info("Added - Event: {} is busy", group.getName());
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


    @GetMapping("add_tutor")
    public ResponseEntity<Object> addTutor(HttpServletRequest request)
    {
        Tutor tutor = new Tutor();
        tutor.setName(request.getParameter("name"));
        try
        {
            tutorService.add(tutor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e)
        {
            log.info("Added - Event: {} is busy", tutor.getName());
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


    @GetMapping("add_subject")
    public ResponseEntity<Object> addSubject(HttpServletRequest request)
    {
        Subject subject = new Subject();
        subject.setName(request.getParameter("name"));
        try
        {
            subjectService.add(subject);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e)
        {
            log.info("Added - Event: {} is busy", subject.getName());
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }




    @GetMapping("add_auditorium")
    public ResponseEntity<Object> addAuditorium(HttpServletRequest request)
    {
        Auditorium auditorium = new Auditorium();
        auditorium.setAddress(request.getParameter("address"));
        try
        {
            auditoriumService.add(auditorium);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e)
        {
            log.info("Added - Event: {} is busy", auditorium.getAddress());
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}