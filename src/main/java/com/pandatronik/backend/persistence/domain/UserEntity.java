package com.pandatronik.backend.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pandatronik.backend.persistence.domain.core.*;
import com.pandatronik.validator.NoPandaInUsernameConstraint;
import com.pandatronik.validator.PasswordConstraint;
import com.pandatronik.validator.UsernameConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UsernameConstraint
@PasswordConstraint
@NoPandaInUsernameConstraint
public class UserEntity implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@NotBlank
	@Size(min = 6, max = 20, message = "Username size must be between 6 and 20")
	@Column(unique = true)
	private String username;

	@NotNull
	@NotBlank
	@Size(min = 6, max = 60, message = "Password size must be between 6 and 60")
	@Column(name = "password")
	private String password;
	// Password is stored in DB table in such format (60 chars), that why max should be different in DTO:
	// $2a$10$3MK16ys612eX0KzFqs7aUOc6Ffji/UoXhJ0JsYC.srNhbbzAHydWC

	@Transient
	private String confirmPassword;

	@NotNull
	@Email(message = "Email address invalid")
	@Column(unique = true)
	private String email;

	@NotNull
	@NotBlank
	@Size(max = 20, message = "FirstName size must be between 1 and 20")
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@NotBlank
	@Size(max = 20, message = "LastName size must be between 1 and 20")
	@Column(name = "last_name")
	private String lastName;

	private boolean enabled;

	/* Many users with the same Plan*/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "plan_id")
	private Plan plan;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserRole> userRoles = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, // if we delete user we want to their token be deleted too
			fetch = FetchType.EAGER,
			mappedBy = "user")
	private Set<PasswordResetToken> passwordResetTokens = new HashSet<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<ImportantEntity> importantEntity;

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
	private List<Important2Entity> important2Entity;

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Important3Entity> important3Entity;

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
	private List<LessImportantEntity> lessImportantEntity;

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
	private List<LessImportant2Entity> lessImportant2Entity;

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
	private List<LessImportant3Entity> lessImportant3Entity;

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<DaysEntity> daysEntity;

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<ExtraordinaryEntity> extraordinaryEntity;

	public Set<PasswordResetToken> getPasswordResetTokens() {
		return passwordResetTokens;
	}

	public void setPasswordResetTokens(Set<PasswordResetToken> passwordResetTokens) {
		this.passwordResetTokens = passwordResetTokens;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
		return authorities;
	}

}