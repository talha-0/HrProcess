package com.group8.announcement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AnnouncementService {
    private AnnouncementRepository announcementRepository;

    public void saveAnnouncement(Announcement announcement) {
        announcement.setDate(LocalDate.now());
        announcementRepository.save(announcement);
    }
}

