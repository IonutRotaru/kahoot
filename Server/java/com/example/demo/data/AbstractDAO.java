package com.example.demo.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import  java.lang.reflect.ParameterizedType;




public class AbstractDAO<T> { 
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
	
	private final Class<T> type ;
	

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		
		this.type=(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	

	public String queryInsert() {  // se genereaza query-ul pentru insert
		StringBuilder s = new StringBuilder();
		s.append("insert ");
		s.append(" into ");
		s.append(type.getSimpleName()); //numele tabelului se obtine prin reflexie
		Field[] fields = type.getDeclaredFields();
		int nr=0;
		s.append("( ");
		for(Field f : fields) {//se calculeaza numarul de coloane
			nr++;
		}
		for(int i=0;i<nr-1;i++) {//numele coloanelor sunt adaugete tot prin reflexie
			s.append(fields[i].getName());
			s.append(",");
		}
		s.append(fields[nr-1].getName());
		s.append(") ");
		s.append(" values ");
		s.append(" ( ");
		for(int i =0; i<nr-1; i++) {// se adauga atatea ? cate coloane are tabela
			s.append("?,");
		}
		s.append("?);");
		return s.toString();
	}

	public String queryDelete(String field) {//queryul pentru delete
		StringBuilder s = new StringBuilder();
		s.append("delete from ");
		s.append(type.getSimpleName()); //numele tabelei obtinut prin reflexie
		s.append(" where " +field+"=");
		s.append("?;");
		return s.toString();
	}
	

	public String queryUpdate(String field1, String field2) {//queryul pentru update
		StringBuilder s = new StringBuilder();
		s.append("update ");
		s.append(type.getSimpleName());//numele tabelului se obtine prin reflexie
		s.append(" set ");
		s.append(field1+"=? ");
		s.append(" where ");
		s.append(field2+"=?;");
		return s.toString();
		
	}

	public String queryFindBy(String field) {// queryul pentru gasirea unei tuple dupa o conditie
		StringBuilder s = new StringBuilder();
		s.append("select * from ");
		s.append(type.getSimpleName());//numele tabelului se obtine prin reflexie
		s.append(" where ");
		s.append(field+"=?;");
		return s.toString();
	}

	public String queryFindAll() {//queryul pentru afisarea tuturor tuplelor
		StringBuilder s = new StringBuilder();
		s.append("select * from ");
		s.append(type.getSimpleName()); //numele tabelului se obtine prin reflexie
		return s.toString();
	} 

	public String queryMaxValue(String field) {// queryul folosit pentru gasirea valorii maxime de pe o coloana
		StringBuilder s = new StringBuilder();
		s.append("select max("+field+")"+" from "+type.getSimpleName()+";"); //numele tabelului se obtine prin reflexie
		return s.toString();
		
	}

	public List<T> listAll() {// afisarea tuturor tuplelor
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String query = queryFindAll(); 
		try {
			connection = ConnectionClass.getConnection();//se face conexiunea la baza de date
			statement = connection.prepareStatement(query); //executarea queryului
			rs=statement.executeQuery();
			
		}catch(SQLException e) {
			
		}

		return createObjects(rs); //se returnaza valorile sub forma unei liste de obiecte T
	}
	
	
	

	private List<T> createObjects(ResultSet resultSet) {// creaza o lista de obiecte T din tuplele returnate dintr-un tabel
		List<T> list = new ArrayList<T>();

		try {
			while (resultSet.next()) {
				
				T instance = type.getDeclaredConstructor().newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					System.out.println("---" + instance.toString());
					System.out.println("+++"+value.toString());
					System.out.println("field"+field.toString());

					method.invoke(instance, value);

				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch(Exception e) {
			
		}
		return list;
	}
	

	public void insert(T t) {// inserarea intr-un tabel a unui obiect de tip T
		Connection connection = null;
		PreparedStatement statement = null;
		String query = queryInsert(); 
		try {
			connection = ConnectionClass.getConnection(); //crearea conexiunii
			statement = connection.prepareStatement(query);
			Field[] fields = type.getDeclaredFields(); //se obtin numele variabilelor instanta ale clasei 
			for(int i=0;i<fields.length;i++) {
				 Field field=fields[i];
				    field.setAccessible(true); //se face din private accesibil
				    Object value=field.get(t);// se obtine valoarea variabilei
				    statement.setObject((i+1), value);
			}
			statement.execute();
			
		}catch(Exception e) {
			
		}
		
	}

	public void delete(String field1, Object field2) { //se executa stergerea
		Connection connection = null;
		PreparedStatement statement = null;
		String query = queryDelete(field1); 
		try {
			connection = ConnectionClass.getConnection();//se face conexiunea
			statement = connection.prepareStatement(query);
		    statement.setObject(1, field2);
			statement.execute();
		}catch(Exception e) {
			
		}
		
		
	}

	public List<T> findBy(String field, Object value) {//se executa operatia de gasire dupa un criteriu
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String query = queryFindBy(field);
		try {
			connection = ConnectionClass.getConnection();//se face conexiunea
			statement = connection.prepareStatement(query);
		    statement.setObject(1, value);
			rs=statement.executeQuery(); 
		}catch(Exception e) {
			
		}
		
		
		return createObjects(rs);//se returnaza valorile sub forma unei liste de obiecte T
		
	}

	public void update(String field1, String field2, Object value1, Object value2) {//se executa operatia de update
		Connection connection = null;
		PreparedStatement statement = null;
		String query = queryUpdate(field1,field2); 
		try {
			connection = ConnectionClass.getConnection();//se face conexiunea
			statement = connection.prepareStatement(query);
		    statement.setObject(1, value1);
		    statement.setObject(2, value2);
			statement.execute();//se executa
		}catch(Exception e) {
			
		}
		
		
	}


}
