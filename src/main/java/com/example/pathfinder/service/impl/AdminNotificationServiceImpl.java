package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Notification;
import com.example.pathfinder.model.view.NotificationViewModel;
import com.example.pathfinder.repository.NotificationRepository;
import com.example.pathfinder.service.AdminNotificationService;
import com.example.pathfinder.service.events.UserRegisteredEvent;
import org.modelmapper.ModelMapper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdminNotificationServiceImpl implements AdminNotificationService {
  private final NotificationRepository notificationRepository;
  private final ModelMapper modelMapper;

  public AdminNotificationServiceImpl(NotificationRepository notificationRepository, ModelMapper modelMapper) {
    this.notificationRepository = notificationRepository;
    this.modelMapper = modelMapper;
  }

  @EventListener
  public void handleUserRegisteredEvent(UserRegisteredEvent event) {
    Notification notification = this.modelMapper.map(event, Notification.class);
    notificationRepository.save(notification);
  }

  public Collection<NotificationViewModel> getNotifications() {
    return this.notificationRepository
            .findAll().stream()
            .map(n -> this.modelMapper.map(n, NotificationViewModel.class))
            .collect(Collectors.toList());
  }
}
