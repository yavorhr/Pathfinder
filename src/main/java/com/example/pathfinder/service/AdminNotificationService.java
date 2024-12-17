package com.example.pathfinder.service;

import com.example.pathfinder.model.view.NotificationViewModel;

import java.util.Collection;

public interface AdminNotificationService {

  Collection<NotificationViewModel> getNotifications();
}
