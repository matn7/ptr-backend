package com.pandatronik.backend.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pandatronik.backend.persistence.domain.core.*;
import com.pandatronik.validator.NoPandaInUsernameConstraint;
import com.pandatronik.validator.PasswordConstraint;
import com.pandatronik.validator.UsernameConstraint;
import lombok.*;
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
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
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