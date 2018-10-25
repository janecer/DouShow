package com.miyue.doushow.lib_base.lib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class VLListMap<K,V>
{
	protected Entry mHead;
	protected HashMap<K,Entry> mMap;
	
	protected class Entry
	{
		protected Entry mPrev;
		protected Entry mNext;
		protected K mKey;
		protected V mValue;
		
		public Entry()
		{
			mPrev = this;
			mNext = this;
			mKey = null;
			mValue = null;
		}
		
		public void insertAfter(Entry prev)
		{
			Entry next = prev.mNext;
			mPrev = prev;
			mNext = next;
			prev.mNext = this;
			next.mPrev = this;
		}
		
		public void insertBefore(Entry next)
		{
			Entry prev = next.mPrev;
			mPrev = prev;
			mNext = next;
			prev.mNext = this;
			next.mPrev = this;
		}
		
		public void removeFrom(Entry head)
		{
			if(this==head) return;
			Entry prev = mPrev;
			Entry next = mNext;
			prev.mNext = next;
			next.mPrev = prev;
		}
	}
	
	public VLListMap()
	{
		mHead = new Entry();
		mMap = new HashMap<K,Entry>();
	}
	
	public Iterator<K> keys()
	{
		return new VLKeyIterator();
	}
	
	public Iterator<V> values()
	{
		return new VLValueIterator();
	}
	
	private class VLKeyIterator implements Iterator<K>
	{
		private Entry mEntry;
		private boolean mCanRemove;
		
		public VLKeyIterator()
		{
			mEntry = mHead;
			mCanRemove = false;
		}
		
		@Override
		public boolean hasNext() 
		{
			return (mEntry.mNext!=mHead);
		}

		@Override
		public K next() 
		{
			synchronized(VLListMap.this)
			{
				if(mEntry.mNext==mHead) throw new NoSuchElementException();
				mEntry = mEntry.mNext;
				mCanRemove = true;
				return mEntry.mKey;
			}
		}

		@Override
		public void remove() 
		{
			synchronized(VLListMap.this)
			{
				if(mCanRemove==false) throw new IllegalStateException();
				mEntry.removeFrom(mHead);
				mMap.remove(mEntry.mKey);
				mCanRemove = false;
			}
		}
	}
	
	public V getValueAt(int index)
	{
		int size = mMap.size();
		Entry entry = mHead;
		for(int i=0;i<=index;i++) entry = entry.mNext;
		return entry.mValue;
	}
	
	public K getKeyAt(int index)
	{
		int size = mMap.size();
		Entry entry = mHead;
		for(int i=0;i<=index;i++) entry = entry.mNext;
		return entry.mKey;
	}
	
	public int size()
	{
		return mMap.size();
	}
	
	private class VLValueIterator implements Iterator<V>
	{
		private Entry mEntry;
		private boolean mCanRemove;
		
		public VLValueIterator()
		{
			mEntry = mHead;
			mCanRemove = false;
		}
		
		@Override
		public boolean hasNext() 
		{
			return (mEntry.mNext!=mHead);
		}

		@Override
		public V next() 
		{
			synchronized(VLListMap.this)
			{
				if(mEntry.mNext==mHead) throw new NoSuchElementException();
				mEntry = mEntry.mNext;
				mCanRemove = true;
				return mEntry.mValue;
			}
		}

		@Override
		public void remove() 
		{
			synchronized(VLListMap.this)
			{
				if(mCanRemove==false) throw new IllegalStateException();
				mEntry.removeFrom(mHead);
				mMap.remove(mEntry.mKey);
				mCanRemove = false;
			}
		}
	}
	
	public synchronized void addHead(K key, V value)
	{
		if(mMap.containsKey(key))
		{
			Entry entry = mMap.get(key);
			entry.mValue = value;
			entry.removeFrom(mHead);
			entry.insertAfter(mHead);
		}
		else
		{
			Entry entry = new Entry();
			entry.mKey = key;
			entry.mValue = value;
			entry.insertAfter(mHead);
			mMap.put(key, entry);
		}
	}
	
	public synchronized void addTail(K key, V value)
	{
		if(mMap.containsKey(key))
		{
			Entry entry = mMap.get(key);
			entry.mValue = value;
			entry.removeFrom(mHead);
			entry.insertBefore(mHead);
		}
		else
		{
			Entry entry = new Entry();
			entry.mKey = key;
			entry.mValue = value;
			entry.insertBefore(mHead);
			mMap.put(key, entry);
		}
	}
	
	public synchronized V removeHead()
	{
		Entry entry = mHead.mNext;
		if(entry==mHead) return null;
		entry.removeFrom(mHead);
		mMap.remove(entry.mKey);
		return entry.mValue;
	}
	
	public synchronized V removeTail()
	{
		Entry entry = mHead.mPrev;
		if(entry==mHead) return null;
		entry.removeFrom(mHead);
		mMap.remove(entry.mKey);
		return entry.mValue;
	}
	
	public synchronized V remove(K key)
	{
		Entry entry = mMap.remove(key);
		if(entry==null) return null;
		entry.removeFrom(mHead);
		return entry.mValue;
	}
	
	public synchronized void clear() {
		mHead = new Entry();
		mMap.clear();
	}
	
	public boolean containsKey(K key)
	{
		return mMap.containsKey(key);
	}
	
	public V get(K key)
	{
		Entry entry = mMap.get(key);
		if(entry==null) return null;
		return entry.mValue;
	}
}
