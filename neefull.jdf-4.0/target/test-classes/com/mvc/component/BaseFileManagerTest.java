package com.mvc.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;

import com.mvc.component.file.service.BaseFileManagerImpl;

@Ignore
public class BaseFileManagerTest extends Thread{
	private List<Long> list;

	public BaseFileManagerTest(List<Long> list){
		this.list = list;
	}


	public void run() {
		BaseFileManagerImpl baseFileManagerImpl = new BaseFileManagerImpl();
		for(int i=0;i<10000;i++){
			list.add(baseFileManagerImpl.getNewFileId());
		}
    }

	public static void main(String[] args) throws Exception {
		List<Long> list1 = new ArrayList<Long>(10000);
		List<Long> list2 = new ArrayList<Long>(10000);
		BaseFileManagerTest test1 = new BaseFileManagerTest(list1);
		BaseFileManagerTest test2 = new BaseFileManagerTest(list2);
		test1.start();
		test2.start();
		Thread.sleep(3000);
		int count = 0;
		int i = selfDuplicate(list1);
		count  += i;
		System.out.println("duplicate 1：" + i);
		i = selfDuplicate(list2);
		count  += i;
		System.out.println("duplicate 2：" + i);
		List duplicated = getDuplicate(list1, list2);
		count += duplicated.size();
		System.out.println("Cross duplicate：" + duplicated.size());

		System.out.println("total：" + count);
    }

	private static List<Long> getDuplicate(List<Long> list1,List<Long> list2){
		List<Long> duplicated = new ArrayList<Long>();
		for(Long long1 : list1){
			for(Long long2 : list2){
				if(long1.equals(long2)){
					duplicated.add(long1);
				}
			}
		}
		return duplicated;
	}

	private static int selfDuplicate(List<Long> list){
		Set<Long> set = new HashSet<Long>();
		set.addAll(list);
		return list.size() - set.size();
	}
}
