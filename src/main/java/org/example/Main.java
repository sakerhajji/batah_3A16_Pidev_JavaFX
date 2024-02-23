package org.example;

import Entity.UserAdmin.Admin;
import Services.UserAdmineServices.AdminService;

public class Main {
    public static void main(String[] args) {


       Admin admin=new Admin();
       admin.setMailUtilisateur("saker@gg.tn");
       admin.setMotDePassUtilisateur("222222222");
       AdminService adminService =new AdminService();
       System.out.println(adminService.Login(admin));






    }
}