## JPA 영속성 컨텍스트 와 플러시



1. EntityManager -> 영속성 컨텍스트 -> DB 순으로 저장된다

2. spring에서는 여러 EntityManger가 하나의 영속성 컨텍스트를 공유한다



영속성 컨텍스트에 저장된 상태가 되면 트랜잭션이 커밋되는 시점에 DB에 저장된다



### 영속성 컨텍스트의 이점

1. 캐시사용이 가능해진다

2. 하나의 스레드에서의 캐시만을 사용하며 공유하지 않는다

3. 영속 엔티티의 동일성이 보장된다 -> REPEATABLE READ 등급의 격리수준이 app단에서 지원된다

4. 쓰기 지연처리를 한꺼번에 처리 가능하다 -> batch성 처리로 효율 up

5. Dirty Checking(변경 감지) - 값 수정시 별도의 update가 없어도 commit시 반영된다

   1카 캐시 사용 저장시 스냅샷을 사용하여 변경을 감지한다 -> **@DynamicUpdate**로 효율을 올리자



---



### 플러시

1. 영속성 컨텍스트의 변경 사항들을 데이터베이스에 싱크하는 작업 -> Dirty Checking 발생

2. JPQL 쿼리를 실행시 값이 없는 문제를 해결하기 위해 flush가 실행된다

#### 플러시 모드 옵션

- `em.setFlushMode(FlushModeType.COMMIT);`

  - **FlushModeType.AUTO**

    - 커밋이나 쿼리를 실행할 때 플러시(**기본값**)

  - FlushModeType.COMMIT

    - 커밋 할때만 플러시

      

**JPA는 기본적으로 데이터를 맞추거나 동시성 제어를 데이터베이스 트랙잭션에 위임한다**



---







## Transaction

데이터베이스의 데이터를 조작하는 작업의 단위

ACID의 원칙을 보장한다 : 원자성, 일관성, 격리성, 지속성

1. 원자성 : 부분적 성공없이 일관되게 성공되어야 한다
2. 일관성 : transaction이 끝날 때 DB의 제약조건에 맞는 상태를 보장해야 한다
3. 격리성 : 중간 상태의 데이터를 다른 transaction이 볼 수 없도록 보장하는 성질
4. 지속성 : transaction이 성공했을 경우 해당 결과가 영구적으로 적용됨을 보장



### InnoDb의 Lock

#### Row-level lock

테이블의 row마다 걸리는 lock 

1. shared lock

   read에 대한 lock 설정 `SELECT ... FOR SHARE`등 일부 read작업을 수행할때 row에 lock을 건다

2. exclusive lock

   write에 대한 loack으로 `SELECT ... FOR UPDATE` or `UPDATE`, `DELETE`등의 수정쿼리를 날릴때 거는 lock



#### Record lock

DB의 index record에 걸리는 lock을 의미한다

위와 마찬가지로 shared 와 exclusive lock 두개가 존재한다



#### Gap lock

Gap lock은 **DB index record의 gap에 걸리는 lock**이다

여기서 **gap이란 index 중 DB에 실제 record가 없는 부분**이다

신규 index로 생성되는 값에 대한 범위 lock이 걸린다 (이미 생성된 index에 대해서는 lock이 걸리지 않는다)



---







## Transaction Isolation Level

DB마다 제공하는 격리상태 레벨 



#### Consistent Read

Consistent란 read(`SELECT`)에 대해 DB에서 고유하게 가지고 있는 Snapshot 데이터로써

동시성을 위해서 각 쿼리의 log를 저장하다가 `SELECT`할때 log를 복구하여 사용한다



