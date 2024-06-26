package alessia.LastProjectGestioneEventi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties({"authorities", "credentialsNonExpired", "accountNonExpired", "role"})

public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String surname;
    private String eMail;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = {@JoinColumn(name = "user_id") },
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private List<Event> events = new ArrayList<>();


    public User(String name, String surname, String eMail, String password){
        this.name = name;
        this.surname = surname;
        this.eMail = eMail;
        this.password = password;
        this.role = Role.EVENTPLANNER;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
    @Override
    public String getUsername() {
        return this.eMail;
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
    public boolean isEnabled() {
        return true;
    }
}
