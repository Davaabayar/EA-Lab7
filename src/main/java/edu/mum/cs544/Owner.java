package edu.mum.cs544;

import org.hibernate.annotations.*;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Owner {
	@Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
	//Initial
	@OneToMany (cascade={CascadeType.PERSIST})
	@JoinColumn (name="clientid")
    private List<Pet> pets;

	//1.	@LazyCollection with Extra option
	/*@OneToMany (cascade={CascadeType.PERSIST})
	@JoinColumn (name="clientid")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<Pet> pets;*/

	//2.	Batch fetching
	/*@OneToMany (cascade={CascadeType.PERSIST})
	@JoinColumn (name="clientid")
	@BatchSize(size=50)
	private List<Pet> pets;*/
	//3.	Subselect
	/*@OneToMany(cascade = {CascadeType.PERSIST})
	@JoinColumn(name="clientid")
	@Fetch(FetchMode.SUBSELECT)
	private List<Pet> pets;*/

	public Owner() {
	}
	public Owner(String name) {
		super();
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Pet> getPets() {
		return pets;
	}
	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
    
	
    
}
