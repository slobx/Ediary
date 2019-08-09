package com.iktpreobuka.final_project.entities.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

public class ActivityDTO {

	
	@JsonView(View.Private.class)
	private Long id;
	
	@JsonView(View.Public.class)
	@NotBlank(message = "Name must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Name must not contain white space.")
	@Size(min=2, max=30, message = "Name must be between {min} and {max} characters long.")
	private String name;
	
	@JsonView(View.Admin.class)
	@NotBlank(message = "Code must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Code must not contain white space.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	



	public ActivityDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}

	public ActivityDTO(
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public ActivityDTO() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActivityDTO [id=").append(id).append(", name=").append(name).append(", code=").append(code)
				.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityDTO other = (ActivityDTO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
