-- 秒殺執行存儲過程
DELIMITER $$ --console 換行符 ; 轉化為 $$
--定義存儲過程
--row_count 返迴上一條修改類型SQL的修改行數
--row_count 0:未修改，>0:修改的行數 <0:SQL錯誤/未執行修改SQL
CREATE PROCEDURE  seckill.execute_seckill
  (in v_seckill_id bigint,in v_phone bigint,in v_kill_time timestamp,out r_result int )
  begin
    declare insert_count int default 0;
    START TRANSACTION ;
    insert ignore into success_killed
      (seckill_id,user_phone,create_time,state)
      values(v_seckill_id,v_phone,v_kill_time,0);
    select row_count() into insert_count;
    IF (insert_count=0) then
      ROLLBACK ;
      set r_result=-1;
    ELSEIF (insert_count<0) then
      ROLLBACK ;
      set r_result=-2;
    ELSE
      update seckill
      set number = number-1
        where seckill_id = v_seckill_id
        and end_time > v_kill_time
        and start_time < v_kill_time
        and number > 0;
        select row_count() into insert_count;
        IF(insert_count=0) then
          ROLLBACK ;
          set r_result=0;
        ELSEIF(insert_count<0) then
          ROLLBACK ;
          set r_result=-2;
        ELSE
          COMMIT ;
          SET  r_result=1;
        END IF;
    END IF;
  End;
$$
--存儲過程定義結束
DELIMITER ;
set @r_result=-3;
--執行存儲過程
call execute_seckill(1000,18867858885,now(),@r_result);
--穫取結果
select @r_result;