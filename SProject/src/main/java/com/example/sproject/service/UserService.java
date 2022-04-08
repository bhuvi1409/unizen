package com.example.sproject.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.example.sproject.entity.Admin;
import com.example.sproject.entity.Role;
import com.example.sproject.entity.User;
import com.example.sproject.repository.AdminRepository;
import com.example.sproject.repository.RoleRepository;
import com.example.sproject.repository.UserRepository;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    AdminRepository adminRepository;

    @Autowired PasswordEncoder passwordEncoder;

    public void registerDefaultUser(User user) {
        encodePassword(user);
        userRepo.save(user);
    }

    public List<User> listAll() {
        return userRepo.findAll();
    }

    public User get(Long id) {
        return userRepo.findById(id).get();
    }

    public List<Role> listRoles() {
        return roleRepo.findAll();
    }

    public void save(User user) {
        encodePassword(user);
        userRepo.save(user);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public ByteArrayInputStream exportAgencyData() throws IOException {
        List<Admin> page= (List<Admin>) this.adminRepository.findAll();

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("employee");
        XSSFRow header = sheet.createRow(0);
        XSSFCell headerId = header.createCell(0, CellType.STRING);
        headerId.setCellValue("Id");
        XSSFCell headerEmail = header.createCell(1, CellType.STRING);
        headerEmail.setCellValue("Email");
        XSSFCell headerName = header.createCell(2, CellType.STRING);
        headerName.setCellValue("Name");
        XSSFCell headerSalary = header.createCell(3, CellType.STRING);
        headerSalary.setCellValue("Salary");

        int count = 1;
        for (Admin admin : page) {
            XSSFRow row = sheet.createRow(count);
            row.createCell(0).setCellValue(admin.getId());
            row.createCell(1).setCellValue(admin.getEmail());
            row.createCell(2).setCellValue(admin.getName());
            row.createCell(3).setCellValue(admin.getSalary());
            count++;
        }
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        wb.write(bout);
        wb.close();
        return new ByteArrayInputStream(bout.toByteArray());
    }

	public void delete(Long id) {
		userRepo.deleteById(id);;
		
	}

}

