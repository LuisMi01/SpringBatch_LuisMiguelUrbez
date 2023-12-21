package io.uax.banco.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Usuario.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Usuario_ {

	
	/**
	 * @see io.uax.banco.domain.Usuario#transactionType
	 **/
	public static volatile SingularAttribute<Usuario, String> transactionType;
	
	/**
	 * @see io.uax.banco.domain.Usuario#accountId
	 **/
	public static volatile SingularAttribute<Usuario, String> accountId;
	
	/**
	 * @see io.uax.banco.domain.Usuario#amount
	 **/
	public static volatile SingularAttribute<Usuario, Double> amount;
	
	/**
	 * @see io.uax.banco.domain.Usuario#id
	 **/
	public static volatile SingularAttribute<Usuario, Long> id;
	
	/**
	 * @see io.uax.banco.domain.Usuario#transactionDate
	 **/
	public static volatile SingularAttribute<Usuario, String> transactionDate;
	
	/**
	 * @see io.uax.banco.domain.Usuario
	 **/
	public static volatile EntityType<Usuario> class_;

	public static final String TRANSACTION_TYPE = "transactionType";
	public static final String ACCOUNT_ID = "accountId";
	public static final String AMOUNT = "amount";
	public static final String ID = "id";
	public static final String TRANSACTION_DATE = "transactionDate";

}

