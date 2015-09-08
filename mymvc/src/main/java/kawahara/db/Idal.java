package kawahara.db;

import java.util.Collection;

import kawahara.models.AnswerModel;

import org.hibernate.Session;

public interface Idal<T, K> {
	public K create(T newItem);
	public T read(K key);
	public boolean update(K key, T toUpdate);
	public void delete(K key);
	public Collection<T> getItems();
}