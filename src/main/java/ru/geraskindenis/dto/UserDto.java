package ru.geraskindenis.dto;

import ru.geraskindenis.in.commands.Role;

public record UserDto(Long id, String login, String password, Role role) {
}

