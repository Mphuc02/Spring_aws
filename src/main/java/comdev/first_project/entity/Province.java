package comdev.first_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lc_province")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Province {
    @Id
    @Column(name = "prv_id")
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "shortname", length = 100)
    private String shortName;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "dcsr", length = 200)
    private String dcsr;

    @Column(name = "region_id")
    private Integer regionID;

    @Column(name = "country_id")
    private Integer countryID;

    @Column(name = "status")
    private Integer status;
}