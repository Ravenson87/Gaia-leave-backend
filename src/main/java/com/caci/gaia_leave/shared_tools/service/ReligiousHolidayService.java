package com.caci.gaia_leave.shared_tools.service;

import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.model.request.UserUsedFreeDays;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.request.UserUsedFreeDaysRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReligiousHolidayService {

    private final UserUsedFreeDaysRepository userUsedFreeDaysRepository;
    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;


    public void setReligiousHoliday(Integer userId) {

        //Find userFreeDays by userId
        UserUsedFreeDays usedHoliday = userUsedFreeDaysRepository.findReligiousFreeDaysForUser(userId);
        if(!userRepository.existsById(userId)){
            throw new CustomException("User with id: " + userId + " does not exist");
        }
        if (usedHoliday == null) {
            throw new CustomException("User with id: " + userId + " does not have religious holidays");
        }

        //find calendar buy the time of function calling. If there is now day in calendar
        // it means that calendar is not populated yet for new year
        Optional<Calendar> calendar = calendarRepository.findById(usedHoliday.getCalendarId());
        if (calendar.isEmpty()) {
            throw new CustomException("There is no such day in this calendar with id " + usedHoliday.getCalendarId());
        }


        // Get date for next year
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(calendar.get().getDate().toString());
        } catch (ParseException e) {
            throw new CustomException("Problem while parsing date");
        }
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.add(java.util.Calendar.YEAR, 1);
        Date religiousHolidayDate = cal.getTime();

        Optional<Calendar> calendarIdForReligiousHoliday = calendarRepository.findByDate(religiousHolidayDate);
        if (calendarIdForReligiousHoliday.isEmpty()) {
            throw new CustomException("There is no such day in this calendar with date " + religiousHolidayDate);
        }

        //Set all fields for new object, ant change only calendarId
        UserUsedFreeDays religiousHolidayToSet = new UserUsedFreeDays();
        religiousHolidayToSet.setCalendarId(calendarIdForReligiousHoliday.get().getId());
        religiousHolidayToSet.setUserId(userId);
        religiousHolidayToSet.setFreeDayTypeId(usedHoliday.getFreeDayTypeId());
        //Create new userUsedFreeDay
        userUsedFreeDaysRepository.save(religiousHolidayToSet);
        System.out.println(usedHoliday);

    }
}
