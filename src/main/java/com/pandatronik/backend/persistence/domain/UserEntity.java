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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.Arrays.stream;

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
	@Size(min = 6, max = 50)
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
	@Email(message = "This not an valid email address")
	@Column(unique = true)
	private String email;

	@NotNull
	@NotBlank
	@Size(max = 50)
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@NotBlank
	@Size(max = 50)
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

	private String[] authorities;

	private boolean isNotLocked;

	private Date lastLoginDate;
	private Date lastLoginDateDisplay;

	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "user")
	private Set<PasswordResetToken> passwordResetTokens = new HashSet<>();

	// Tesks, Days
    @OneToMany(mappedBy = "userEntity")
    private List<ImportantEntity> importantEntity;

	@OneToMany(mappedBy = "userEntity")
	private List<Important2Entity> important2Entity;

    @OneToMany(mappedBy = "userEntity")
    private List<Important3Entity> important3Entity;

	@OneToMany(mappedBy = "userEntity")
	private List<LessImportantEntity> lessImportantEntity;

	@OneToMany(mappedBy = "userEntity")
	private List<LessImportant2Entity> lessImportant2Entity;

	@OneToMany(mappedBy = "userEntity")
	private List<LessImportant3Entity> lessImportant3Entity;

	@OneToMany(mappedBy = "userEntity")
    private List<DaysEntity> daysEntity;

    @OneToMany(mappedBy = "userEntity")
    private List<ExtraordinaryEntity> extraordinaryEntity;

	public Set<PasswordResetToken> getPasswordResetTokens() {
		return passwordResetTokens;
	}

	public void setPasswordResetTokens(Set<PasswordResetToken> passwordResetTokens) {
		this.passwordResetTokens = passwordResetTokens;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return stream(this.authorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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