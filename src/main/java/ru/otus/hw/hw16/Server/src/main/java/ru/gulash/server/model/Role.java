package ru.gulash.server.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Enum, представляющее роли в системе с поддержкой иерархии.
 * Каждая роль может включать другие роли, предоставляя доступ к их правам.
 */
public enum Role {
    /**
     * Базовая роль, предоставляющая только права на чтение
     */
    READONLY(),

    /**
     * Базовая роль, предоставляющая только права на запись
     */
    WRITEONLY(),

    /**
     * Роль обычного пользователя, включающая права READONLY
     */
    USER(READONLY),

    /**
     * Роль администратора, включающая все права USER и WRITEONLY
     */
    ADMIN(USER, WRITEONLY);

    private final Set<Role> includedRoles;

    /**
     * Создает роль без включенных дочерних ролей.
     */
    Role() {
        this.includedRoles = new HashSet<>();
    }

    /**
     * Создает роль с указанными дочерними ролями.
     *
     * @param includedRoles массив ролей, которые будут включены в создаваемую роль
     */
    Role(Role... includedRoles) {
        this.includedRoles = new HashSet<>();
        for (Role role : includedRoles) {
            this.includedRoles.add(role);
            // Добавляем все роли, которые включает данная роль
            this.includedRoles.addAll(role.includedRoles);
        }
    }

    /**
     * Проверяет, имеет ли пользователь с указанной ролью доступ к требуемой роли.
     *
     * @param userRole     роль пользователя, для которой проверяется доступ
     * @param requiredRole роль, доступ к которой необходимо проверить
     */
    public static boolean hasAccess(Role userRole, Role requiredRole) {
        return userRole.includes(requiredRole);
    }

    /**
     * Проверяет, включает ли данная роль указанную роль
     */
    public boolean includes(Role role) {
        return this == role || this.includedRoles.contains(role);
    }

/*    // Демонстрация использования
    public static void main(String[] args) {
        System.out.println("=== Демонстрация иерархии ролей ===\n");

        // Проверяем иерархию
        System.out.println("ADMIN включает USER: " + Role.ADMIN.includes(Role.USER));
        System.out.println("ADMIN включает READONLY: " + Role.ADMIN.includes(Role.READONLY));
        System.out.println("ADMIN включает WRITEONLY: " + Role.ADMIN.includes(Role.WRITEONLY));
        System.out.println("USER включает READONLY: " + Role.USER.includes(Role.READONLY));
        System.out.println("USER включает WRITEONLY: " + Role.USER.includes(Role.WRITEONLY));
        System.out.println("READONLY включает USER: " + Role.READONLY.includes(Role.USER));

        // Пример функции проверки доступа
        System.out.println("Пользователь с ролью " + Role.ADMIN + " имеет доступ к " + Role.READONLY +
            ": " + hasAccess(Role.ADMIN, Role.READONLY));
        System.out.println("Пользователь с ролью " + Role.USER + " имеет доступ к " + Role.WRITEONLY +
            ": " + hasAccess(Role.USER, Role.WRITEONLY));
        System.out.println("Пользователь с ролью " + Role.READONLY + " имеет доступ к " + Role.ADMIN +
            ": " + hasAccess(Role.READONLY, Role.ADMIN));
    }*/
}