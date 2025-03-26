package no.hvl.dat107;

import java.util.List;

public interface TodoDAO {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Todo finnTodoMedPk(int id);
	
	/**
	 * 
	 * @return
	 */
	public List<Todo> finnAlleTodos();
	
	/**
	 * 
	 * @param tekst
	 * @return
	 */
	public Todo finnTodoMedTekst(String tekst);
	
	/**
	 * 
	 * @param tekst
	 * @return
	 */
	public List<Todo>  finnTodosMedTekst(String tekst);
	
	/**
	 * 
	 * @param id
	 * @param tekst
	 */
	public void lagreNyTodo(int id, String tekst);
	
	/**
	 * 
	 * @param id
	 */
	public void slettTodoMedPk(int id);
	
	/**
	 * 
	 * @param todo
	 */
	public void oppdaterTodo(Todo todo);
	
	/**
	 * 
	 * @param id
	 * @param tekst
	 */
	public void oppdaterTekst(int id, String tekst);
}
