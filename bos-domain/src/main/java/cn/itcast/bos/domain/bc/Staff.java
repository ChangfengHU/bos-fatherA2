package cn.itcast.bos.domain.bc;
// Generated 2017-7-18 12:28:45 by Hibernate Tools 3.2.2.GA

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Staff generated by hbm2java
 */
@Entity
@Table(name = "bc_staff", catalog = "bos", uniqueConstraints = @UniqueConstraint(columnNames = "TELEPHONE"))
public class Staff implements java.io.Serializable {
	private String id;
	private String name;
	private String telephone;
	private Integer haspda;// PDA
	private Integer deltag = 0;// 0在职 0 离职
	private String station;// 单位
	private String standard;// 取派员收派标准
	private Set<DecidedZone> decidedZones = new HashSet<DecidedZone>(0);

	public Staff() {
	}

	@Override
	public String toString() {
		return "Staff{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", telephone='" + telephone + '\'' +
				", haspda=" + haspda +
				", deltag=" + deltag +
				", station='" + station + '\'' +
				", standard='" + standard + '\'' +
				'}';
	}

	public Staff(String telephone) {
		this.telephone = telephone;
	}

	public Staff(String name, String telephone, Integer haspda, Integer deltag, String station, String standard, Set<DecidedZone> decidedZones) {
		this.name = name;
		this.telephone = telephone;
		this.haspda = haspda;
		this.deltag = deltag;
		this.station = station;
		this.standard = standard;
		this.decidedZones = decidedZones;
	}

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TELEPHONE", unique = true, nullable = false, length = 20)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "HASPDA")
	public Integer getHaspda() {
		return this.haspda;
	}

	public void setHaspda(Integer haspda) {
		this.haspda = haspda;
	}

	@Column(name = "DELTAG")
	public Integer getDeltag() {
		return this.deltag;
	}

	public void setDeltag(Integer deltag) {
		this.deltag = deltag;
	}

	@Column(name = "STATION", length = 40)
	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	@Column(name = "STANDARD", length = 100)
	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "staff")
	// json-plugin插件提供注解 在进行json序列化 不会调用该方法
	@JSON(serialize = false)
	@JSONField(serialize = false)
	public Set<DecidedZone> getDecidedZones() {
		return this.decidedZones;
	}

	public void setDecidedZones(Set<DecidedZone> decidedZones) {
		this.decidedZones = decidedZones;
	}

}
