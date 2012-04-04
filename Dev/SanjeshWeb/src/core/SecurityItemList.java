package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SecurityItemList implements List<SecurityItem> {

	SecurityItem parentItem;
	private ArrayList<SecurityItem> list = new ArrayList<SecurityItem>();

	public SecurityItemList(SecurityItem parentItem) {
		this.parentItem = parentItem;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<SecurityItem> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(SecurityItem e) {
		e.parentList = this;
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		if (!(o instanceof SecurityItemList))
			return false;
		((SecurityItem) o).parentList = null;
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends SecurityItem> c) {
		for( SecurityItem si : c )
			si.parentList = this;
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends SecurityItem> c) {
		for( SecurityItem si : c )
			si.parentList = this;
		return list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for( Object o : c )
			((SecurityItem)o).parentList = this;
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		for( SecurityItem si : list)
			si.parentList = null;
		list.clear();
	}

	@Override
	public SecurityItem get(int index) {
		return list.get(index);
	}

	@Override
	public SecurityItem set(int index, SecurityItem element) {
		list.get(index).parentList = null;
		element.parentList = this;
		return list.set(index, element);
	}

	@Override
	public void add(int index, SecurityItem element) {
		element.parentList = this;
		list.add(index, element);
	}

	@Override
	public SecurityItem remove(int index) {
		SecurityItem si = list.remove(index);
		si.parentList = null;
		return si;
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<SecurityItem> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<SecurityItem> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public List<SecurityItem> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

}
