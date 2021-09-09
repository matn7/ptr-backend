package com.pandatronik.backend.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.validator.EmailConstraint;
import com.pandatronik.validator.NoPandaInUsernameConstraint;
import com.pandatronik.validator.PasswordConstraint;
import com.pandatronik.validator.UsernameConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UsernameConstraint(message = "{not.allowed.username.message}")
@PasswordConstraint
@NoPandaInUsernameConstraint(message = "{panda.in.username.message}")
@EmailConstraint(message = "{invalid.email.address.message}")
public class UserEntity implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@NotBlank
	@Size(min = 5, max = 20)
	@Column(unique = true)
	private String username;

	@NotNull
	@NotBlank
	@Size(min = 6, max = 60)
	@Column(name = "password")
	private String password;

	@Transient
	private String confirmPassword;

	@NotNull
//	@Email(message = "{invalid.email.address}")
	@Column(unique = true)
	private String email;

	@NotNull
	@NotBlank
	@Size(max = 20)
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@NotBlank
	@Size(max = 20)
	@Column(name = "last_name")
	private String lastName;

	private boolean enabled;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "plan_id")
	private Plan plan;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserRole> userRoles = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "user"
	)
	private Set<PasswordResetToken> passwordResetTokens = new HashSet<>();

	private boolean isNotLocked;

	private Date lastLoginDate;
	private Date lastLoginDateDisplay;

	// Entities related to AppUser
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
    private Set<ImportantEntity> importantEntity = new HashSet<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
	private Set<Important2Entity> important2Entity = new HashSet<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
    private Set<Important3Entity> important3Entity = new HashSet<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
	private Set<LessImportantEntity> lessImportantEntity = new HashSet<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
	private Set<LessImportant2Entity> lessImportant2Entity = new HashSet<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
	private Set<LessImportant3Entity> lessImportant3Entity = new HashSet<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
    private Set<DaysEntity> daysEntity = new HashSet<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
    private Set<ExtraordinaryEntity> extraordinaryEntity = new HashSet<>();

	public Set<PasswordResetToken> getPasswordResetTokens() {
		return passwordResetTokens;
	}

	public void setPasswordResetTokens(Set<PasswordResetToken> passwordResetTokens) {
		this.passwordResetTokens = passwordResetTokens;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isNotLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}


}