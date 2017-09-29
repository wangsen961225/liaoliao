package MyTest;

import java.util.ArrayList;
import java.util.List;
 
public class ddd {
@SuppressWarnings("unchecked")
public static void main(String args[]){
     
    @SuppressWarnings("rawtypes")
    List <String> list = new ArrayList<String>();
    for(int i=0;i<200;i++){
        list.add(i+"");
    }
    list = list.subList(100, list.size());
    for(String s :list){
        System.out.println("删除后的数据："+s);
    }
    System.out.println("s");
/*=====================================
2017-07-22 17:54:12 0xb38 INNODB MONITOR OUTPUT
=====================================
Per second averages calculated from the last 11 seconds
-----------------
BACKGROUND THREAD
-----------------
srv_master_thread loops: 487 srv_active, 0 srv_shutdown, 34025 srv_idle
srv_master_thread log flush and writes: 34512
----------
SEMAPHORES
----------
OS WAIT ARRAY INFO: reservation count 5859
OS WAIT ARRAY INFO: signal count 5744
RW-shared spins 0, rounds 6145, OS waits 2945
RW-excl spins 0, rounds 260, OS waits 5
RW-sx spins 0, rounds 0, OS waits 0
Spin rounds per wait: 6145.00 RW-shared, 260.00 RW-excl, 0.00 RW-sx
------------------------
LATEST DETECTED DEADLOCK
------------------------
2017-07-22 15:11:57 0x1db8
*** (1) TRANSACTION:
TRANSACTION 53273, ACTIVE 0 sec starting index read
mysql tables in use 1, locked 1
LOCK WAIT 5 lock struct(s), heap size 1136, 2 row lock(s), undo log entries 1
MySQL thread id 6, OS thread handle 7460, query id 69118 192.168.0.222 root updating

update ll_user_info set add_ip=null, add_time='2017-07-20 14:20:51', avatar='http://q.qlogo.cn/qqapp/101409215/0C3595CCE7D6BF1375BC58BD61B0595A/100', day_money=0.0, freeze_money=0.0, login_time='2017-07-22 15:08:05', mobile=null, nick_name='栀子花开', parent_id=null, pass_word=null, pay_money=7549.0, status=1, to_bank_money=0.0, total_money=23177.0, vip_status=1 where id=29

*** (1) WAITING FOR THIS LOCK TO BE GRANTED:
RECORD LOCKS space id 338 page no 3 n bits 104 index PRIMARY of table `liaoliao`.`ll_user_info` trx id 53273 lock_mode X locks rec but not gap waiting
Record lock, heap no 31 PHYSICAL RECORD: n_fields 18; compact format; info bits 0
 0: len 4; hex 8000001d; asc     ;;
 1: len 6; hex 00000000d017; asc       ;;
 2: len 7; hex 5f000001fd1a10; asc _      ;;
 3: SQL NULL;
 4: len 12; hex e6a080e5ad90e88ab1e5bc80; asc             ;;
 5: SQL NULL;
 6: len 30; hex 687474703a2f2f712e716c6f676f2e636e2f71716170702f313031343039; asc http://q.qlogo.cn/qqapp/101409; (total 70 bytes);
 7: len 4; hex 80000001; asc     ;;
 8: len 4; hex 80000001; asc     ;;
 9: SQL NULL;
 10: len 8; hex 00000000408ed640; asc     @  @;;
 11: len 8; hex 00000000007dbd40; asc      } @;;
 12: len 8; hex 0000000000000000; asc         ;;
 13: len 8; hex 0000000000000000; asc         ;;
 14: len 8; hex 0000000000000000; asc         ;;
 15: len 5; hex 999d2cf205; asc   ,  ;;
 16: len 5; hex 999d28e533; asc   ( 3;;
 17: SQL NULL;

*** (2) TRANSACTION:
TRANSACTION 53274, ACTIVE 0 sec starting index read, thread declared inside InnoDB 5000
mysql tables in use 1, locked 1
5 lock struct(s), heap size 1136, 2 row lock(s), undo log entries 1
MySQL thread id 14, OS thread handle 7608, query id 69122 192.168.0.222 root updating
update ll_user_info set add_ip=null, add_time='2017-07-20 14:20:51', avatar='http://q.qlogo.cn/qqapp/101409215/0C3595CCE7D6BF1375BC58BD61B0595A/100', day_money=0.0, freeze_money=0.0, login_time='2017-07-22 15:08:05', mobile=null, nick_name='栀子花开', parent_id=null, pass_word=null, pay_money=7549.0, status=1, to_bank_money=0.0, total_money=23135.0, vip_status=1 where id=29
*** (2) HOLDS THE LOCK(S):
RECORD LOCKS space id 338 page no 3 n bits 104 index PRIMARY of table `liaoliao`.`ll_user_info` trx id 53274 lock mode S locks rec but not gap
Record lock, heap no 31 PHYSICAL RECORD: n_fields 18; compact format; info bits 0
 0: len 4; hex 8000001d; asc     ;;
 1: len 6; hex 00000000d017; asc       ;;
 2: len 7; hex 5f000001fd1a10; asc _      ;;
 3: SQL NULL;
 4: len 12; hex e6a080e5ad90e88ab1e5bc80; asc             ;;
 5: SQL NULL;
 6: len 30; hex 687474703a2f2f712e716c6f676f2e636e2f71716170702f313031343039; asc http://q.qlogo.cn/qqapp/101409; (total 70 bytes);
 7: len 4; hex 80000001; asc     ;;
 8: len 4; hex 80000001; asc     ;;
 9: SQL NULL;
 10: len 8; hex 00000000408ed640; asc     @  @;;
 11: len 8; hex 00000000007dbd40; asc      } @;;
 12: len 8; hex 0000000000000000; asc         ;;
 13: len 8; hex 0000000000000000; asc         ;;
 14: len 8; hex 0000000000000000; asc         ;;
 15: len 5; hex 999d2cf205; asc   ,  ;;
 16: len 5; hex 999d28e533; asc   ( 3;;
 17: SQL NULL;

*** (2) WAITING FOR THIS LOCK TO BE GRANTED:
RECORD LOCKS space id 338 page no 3 n bits 104 index PRIMARY of table `liaoliao`.`ll_user_info` trx id 53274 lock_mode X locks rec but not gap waiting
Record lock, heap no 31 PHYSICAL RECORD: n_fields 18; compact format; info bits 0
 0: len 4; hex 8000001d; asc     ;;
 1: len 6; hex 00000000d017; asc       ;;
 2: len 7; hex 5f000001fd1a10; asc _      ;;
 3: SQL NULL;
 4: len 12; hex e6a080e5ad90e88ab1e5bc80; asc             ;;
 5: SQL NULL;
 6: len 30; hex 687474703a2f2f712e716c6f676f2e636e2f71716170702f313031343039; asc http://q.qlogo.cn/qqapp/101409; (total 70 bytes);
 7: len 4; hex 80000001; asc     ;;
 8: len 4; hex 80000001; asc     ;;
 9: SQL NULL;
 10: len 8; hex 00000000408ed640; asc     @  @;;
 11: len 8; hex 00000000007dbd40; asc      } @;;
 12: len 8; hex 0000000000000000; asc         ;;
 13: len 8; hex 0000000000000000; asc         ;;
 14: len 8; hex 0000000000000000; asc         ;;
 15: len 5; hex 999d2cf205; asc   ,  ;;
 16: len 5; hex 999d28e533; asc   ( 3;;
 17: SQL NULL;

*** WE ROLL BACK TRANSACTION (2)
------------
TRANSACTIONS
------------
Trx id counter 53881
Purge done for trx's n:o < 53881 undo n:o < 0 state: running but idle
History list length 1594
LIST OF TRANSACTIONS FOR EACH SESSION:
---TRANSACTION 281475149800920, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149800048, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149798304, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149797432, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149795688, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149794816, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149808768, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149806152, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149805280, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149804408, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149803536, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149802664, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149793944, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149793072, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149799176, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
---TRANSACTION 281475149796560, not started
0 lock struct(s), heap size 1136, 0 row lock(s)
--------
FILE I/O
--------
I/O thread 0 state: wait Windows aio (insert buffer thread)
I/O thread 1 state: wait Windows aio (log thread)
I/O thread 2 state: wait Windows aio (read thread)
I/O thread 3 state: wait Windows aio (read thread)
I/O thread 4 state: wait Windows aio (read thread)
I/O thread 5 state: wait Windows aio (read thread)
I/O thread 6 state: wait Windows aio (write thread)
I/O thread 7 state: wait Windows aio (write thread)
I/O thread 8 state: wait Windows aio (write thread)
I/O thread 9 state: wait Windows aio (write thread)
Pending normal aio reads: [0, 0, 0, 0] , aio writes: [0, 0, 0, 0] ,
 ibuf aio reads:, log i/o's:, sync i/o's:
Pending flushes (fsync) log: 0; buffer pool: 0
45096 OS file reads, 5072 OS file writes, 3508 OS fsyncs
0.91 reads/s, 16384 avg bytes/read, 0.00 writes/s, 0.00 fsyncs/s
-------------------------------------
INSERT BUFFER AND ADAPTIVE HASH INDEX
-------------------------------------
Ibuf: size 1, free list len 0, seg size 2, 17 merges
merged operations:
 insert 27, delete mark 0, delete 0
discarded operations:
 insert 0, delete mark 0, delete 0
Hash table size 2267, node heap has 1 buffer(s)
Hash table size 2267, node heap has 1 buffer(s)
Hash table size 2267, node heap has 2 buffer(s)
Hash table size 2267, node heap has 1 buffer(s)
Hash table size 2267, node heap has 1 buffer(s)
Hash table size 2267, node heap has 1 buffer(s)
Hash table size 2267, node heap has 1 buffer(s)
Hash table size 2267, node heap has 1 buffer(s)
0.45 hash searches/s, 2.55 non-hash searches/s
---
LOG
---
Log sequence number 35832500
Log flushed up to   35832500
Pages flushed up to 35832500
Last checkpoint at  35832491
0 pending log flushes, 0 pending chkp writes
2224 log i/o's done, 0.00 log i/o's/second
----------------------
BUFFER POOL AND MEMORY
----------------------
Total large memory allocated 8585216
Dictionary memory allocated 2577276
Buffer pool size   512
Free buffers       0
Database pages     503
Old database pages 0
Modified db pages  0
Pending reads      0
Pending writes: LRU 0, flush list 0, single page 0
Pages made young 0, not young 0
0.00 youngs/s, 0.00 non-youngs/s
Pages read 44983, created 111, written 2428
0.91 reads/s, 0.00 creates/s, 0.00 writes/s
Buffer pool hit rate 977 / 1000, young-making rate 0 / 1000 not 0 / 1000
Pages read ahead 0.00/s, evicted without access 0.36/s, Random read ahead 0.00/s
LRU len: 503, unzip_LRU len: 0
I/O sum[10]:cur[0], unzip sum[0]:cur[0]
--------------
ROW OPERATIONS
--------------
0 queries inside InnoDB, 0 queries in queue
0 read views open inside InnoDB
Process ID=1864, Main thread ID=2352, state: sleeping
Number of rows inserted 2227, updated 485, deleted 21, read 125993
0.00 inserts/s, 0.00 updates/s, 0.00 deletes/s, 5.91 reads/s
----------------------------
END OF INNODB MONITOR OUTPUT
============================*/
}
}
