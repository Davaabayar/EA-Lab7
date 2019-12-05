# EA-Lab7
Hibernate optimization.
Here is summary of the code
In this lab we tried and compared different methods to optimize N+1 problem.

###LazyCollection (N)	
N+1 issue is still there but, query is simplified. It is useful for situations where full data is not needed, for example number of result. size(), iscontain(), isEmpty()
It make use of count() of query.
```java
@OneToMany (cascade={CascadeType.PERSIST})
@JoinColumn (name="clientid")
@LazyCollection(LazyCollectionOption.EXTRA)
private List<Pet> pets;
```    
###Batchsize	(N/batchSize)
Reduce number of database hits by loading batchsize collections at a time.
```java
@OneToMany (cascade={CascadeType.PERSIST})
@JoinColumn (name="clientid")
@BatchSize(size=10)
private List<Pet> pets;
```
###FetchMode.Subselect	(1)	
Subselect query
```java
@OneToMany(cascade = {CascadeType.PERSIST})
@JoinColumn(name="clientid")
@Fetch(FetchMode.SUBSELECT)
private List<Pet> pets;
```
###Join Fetch query	(1)	
Inner join
```java
//association in entity
@OneToMany (cascade={CascadeType.PERSIST})
@JoinColumn (name="clientid")
private List<Pet> pets;
//query in app
query = em.createQuery("from Owner o JOIN FETCH o.pets", Owner.class);
```
###Entity Graph query	1	
```java
EntityGraph<Owner> graph = em.createEntityGraph(Owner.class);
graph.addAttributeNodes("pets");

TypedQuery<Owner> query = em.createQuery("from Owner",Owner.class);
query.setHint("javax.persistence.fetchgraph", graph);

List<Owner> ownerlist = query.getResultList();
```
Here is the main code for App.java
```java
public class App {
   private static EntityManagerFactory emf;
   public static void main(String[] args) throws Exception {
   emf = Persistence.createEntityManagerFactory("cs544");

   long start = System.nanoTime();
   EntityManager em = emf.createEntityManager();
   em.getTransaction().begin();

   TypedQuery<Owner> query = em.createQuery("from Owner", Owner.class);
   List<Owner> ownerlist = query.getResultList();
   for (Owner o : ownerlist) {
      o.getPets().size();
   }

   em.getTransaction().commit();
   em.close();
   long stop = System.nanoTime();

   // stop time
   System.out.println("To fetch this data from the database took " + (stop - start) / 1000000 + " milliseconds.");
   System.exit(0);
 }
}
```
