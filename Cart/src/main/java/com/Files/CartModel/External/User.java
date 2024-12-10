package com.Files.CartModel.External;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private long user_id;
	private String f_name;
	private String l_name;
	private LocalDate user_dob;
	private LocalDate user_reg_date;
	private LocalDateTime user_update_date;
	private String user_password;
	private String user_email;
	private String user_role;

}
