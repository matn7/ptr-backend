package com.pandatronik.backend.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pandatronik.backend.persistence.domain.core.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class UserEntity implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	public UserEntity() {

	}

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
	// pewnie password po szyfrowanie zwieksza rozmiar dzine ?

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

	@OneToMany(cascade = CascadeType.ALL, // if we delete user we want to their token be deleted too
			fetch = FetchType.LAZY,
			mappedBy = "user")
	private Set<PasswordResetToken> passwordResetTokens = new HashSet<>();

	// Tesks, Days
    @OneToMany(mappedBy = "userEntity")
    private List<ImportantEntity> importantEntity;

	@OneToMany(mappedBy = "userEntity")
	private List<ImportantEntity2> importantEntity2;

    @OneToMany(mappedBy = "userEntity")
    private List<ImportantEntity3> importantEntity3;

	@OneToMany(mappedBy = "userEntity")
	private List<LessImportantEntity> lessImportantEntity;

	@OneToMany(mappedBy = "userEntity")
	private List<LessImportantEntity2> lessImportantEntity2;

	@OneToMany(mappedBy = "userEntity")
	private List<LessImportantEntity3> lessImportantEntity3;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

    public List<ImportantEntity> getImportantEntity() {
        return importantEntity;
    }

    public void setImportantEntity(List<ImportantEntity> importantEntity) {
        this.importantEntity = importantEntity;
    }

	public List<ImportantEntity2> getImportantEntity2() {
		return importantEntity2;
	}

	public void setImportantEntity2(List<ImportantEntity2> importantEntity2) {
		this.importantEntity2 = importantEntity2;
	}

    public List<ImportantEntity3> getImportantEntity3() {
        return importantEntity3;
    }

    public void setImportantEntity3(List<ImportantEntity3> importantEntity3) {
        this.importantEntity3 = importantEntity3;
    }

    public List<LessImportantEntity> getLessImportantEntity() {
		return lessImportantEntity;
	}

	public void setLessImportantEntity(List<LessImportantEntity> lessImportantEntity) {
		this.lessImportantEntity = lessImportantEntity;
	}

	public List<LessImportantEntity2> getLessImportantEntity2() {
		return lessImportantEntity2;
	}

	public void setLessImportantEntity2(List<LessImportantEntity2> lessImportantEntity2) {
		this.lessImportantEntity2 = lessImportantEntity2;
	}

	public List<LessImportantEntity3> getLessImportantEntity3() {
		return lessImportantEntity3;
	}

	public void setLessImportantEntity3(List<LessImportantEntity3> lessImportantEntity3) {
		this.lessImportantEntity3 = lessImportantEntity3;
	}

    public List<DaysEntity> getDaysEntity() {
        return daysEntity;
    }

    public void setDaysEntity(List<DaysEntity> daysEntity) {
        this.daysEntity = daysEntity;
    }

    public List<ExtraordinaryEntity> getExtraordinaryEntity() {
        return extraordinaryEntity;
    }

    public void setExtraordinaryEntity(List<ExtraordinaryEntity> extraordinaryEntity) {
        this.extraordinaryEntity = extraordinaryEntity;
    }

    @Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserEntity user = (UserEntity) o;

		return id == user.id;

	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserEntity{");
		sb.append("username='").append(username).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", confirmPassword='").append(confirmPassword).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append(", firstName='").append(firstName).append('\'');
		sb.append(", lastName='").append(lastName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}