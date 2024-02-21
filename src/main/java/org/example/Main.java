package org.example;

import DataBaseSource.DataSource;
import Entity.AdminUser.Admin;
import Services.UserAdmineServices.AdminService;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {


       // Admin admin=new Admin("Sofian","mrabet","sofiane.mrabet@esprit.tn","52225548",date,'H');
        AdminService adminService =new AdminService();
       // adminService.add(admin);
        Admin admin1 =new Admin();
        admin1=adminService.readById(2);
        adminService.delete(admin1);




    }
}