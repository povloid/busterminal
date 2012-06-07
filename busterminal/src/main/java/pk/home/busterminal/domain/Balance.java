package pk.home.busterminal.domain;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;

import pk.home.busterminal.domain.security.UserAccount;

/**
 * Entity class: Balance Balance - баланс
 * 
 */
@Entity
@Table(schema = "public", name = "Balance")
@NamedQueries({
		@NamedQuery(name = "Balance.findAll", query = "select a from Balance a order by a.id"),
		@NamedQuery(name = "Balance.findByPrimaryKey", query = "select a from Balance a where a.id = ?1") })
public class Balance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Index(name = "balance_idx2")
	private BalanceType balanceType;

	@ManyToOne
	@Index(name = "order_idx4")
	private Division division;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@Index(name = "balance_idx1")
	@NotNull
	private Date opTime;

	private String description;

	@NotNull
	@Column(nullable = false)
	private BigDecimal actualSumm;

	@ManyToOne
	@Index(name = "order_idx3")
	@JoinColumn(nullable = false)
	@NotNull
	private UserAccount userAccount;

	/**
	 * Проверка
	 * 
	 * @throws Exception
	 */
	@PrePersist
	@PreUpdate
	public void check() throws Exception {

		if (balanceType != null && balanceType == BalanceType.PLUS) {

			if (actualSumm != null && actualSumm.doubleValue() <= 0)
				throw new Exception(
						"Баланс PLUS - сумма должена быть положительной!");

		}

		if (balanceType != null && balanceType == BalanceType.MINUS) {

			if (actualSumm != null && actualSumm.doubleValue() >= 0)
				throw new Exception(
						"Баланс MINUS - сумма должена быть отрицательной!");

		}

	}

	public Balance() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public BalanceType getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(BalanceType balanceType) {
		this.balanceType = balanceType;
	}

	public BigDecimal getActualSumm() {
		return actualSumm;
	}

	public void setActualSumm(BigDecimal actualSumm) {
		this.actualSumm = actualSumm;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Balance)) {
			return false;
		}
		Balance other = (Balance) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.Balance[ id=" + id + " ]";
	}

}
