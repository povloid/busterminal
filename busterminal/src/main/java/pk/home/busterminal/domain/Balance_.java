package pk.home.busterminal.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pk.home.busterminal.domain.security.UserAccount;

@StaticMetamodel(Balance.class)
public abstract class Balance_ {

	public static volatile SingularAttribute<Balance, Long> id;
	public static volatile SingularAttribute<Balance, Date> opTime;
	public static volatile SingularAttribute<Balance, Division> division;
	public static volatile SingularAttribute<Balance, String> description;
	public static volatile SingularAttribute<Balance, BalanceType> balanceType;
	public static volatile SingularAttribute<Balance, BigDecimal> actualSumm;
	public static volatile SingularAttribute<Balance, UserAccount> userAccount;

}

