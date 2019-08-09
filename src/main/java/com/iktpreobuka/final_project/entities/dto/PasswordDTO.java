package com.iktpreobuka.final_project.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

public class PasswordDTO {

	@JsonView(View.Admin.class)
	@NotBlank(message = "Old password must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Old password must not contain white space.")
	@Size(min=5, max=10, message = "Old password must be between {min} and {max} characters long.")
	private String oldPassword;
	
	@JsonView(View.Admin.class)
	@NotBlank(message = "New password must be provided.")
	@Pattern(regexp = "^\\S*$", message = "New password must not contain white space.")
	@Size(min=5, max=10, message = "New password must be between {min} and {max} characters long.")
	private String newPassword;
	
	@JsonView(View.Admin.class)
	@NotBlank(message = "New confirm password must be provided.")
	@Pattern(regexp = "^\\S*$", message = "New confirm password must not contain white space.")
	@Size(min=5, max=10, message = "New confirm password must be between {min} and {max} characters long.")
	private String newConfirmPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewConfirmPassword() {
		return newConfirmPassword;
	}

	public void setNewConfirmPassword(String newConfirmPassword) {
		this.newConfirmPassword = newConfirmPassword;
	}

	public PasswordDTO() {
		super();
	}

	public PasswordDTO(
			@NotBlank(message = "Old password must be provided.") @Pattern(regexp = "^\\S*$", message = "Old password must not contain white space.") @Size(min = 5, max = 10, message = "Old password must be between {min} and {max} characters long.") String oldPassword,
			@NotBlank(message = "New password must be provided.") @Pattern(regexp = "^\\S*$", message = "New password must not contain white space.") @Size(min = 5, max = 10, message = "New password must be between {min} and {max} characters long.") String newPassword,
			@NotBlank(message = "New confirm password must be provided.") @Pattern(regexp = "^\\S*$", message = "New confirm password must not contain white space.") @Size(min = 5, max = 10, message = "New confirm password must be between {min} and {max} characters long.") String newConfirmPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.newConfirmPassword = newConfirmPassword;
	}
	
	
}
