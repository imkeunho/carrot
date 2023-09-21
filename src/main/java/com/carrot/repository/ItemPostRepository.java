package com.carrot.repository;

import java.util.List;

import com.carrot.domain.ItemPostVO;
import com.carrot.domain.SearchVO;
import com.carrot.domain.TradeVO;

public interface ItemPostRepository {
    public int insert(ItemPostVO vo);
    public int selectCount(SearchVO vo);
    public ItemPostVO selectById(int id);
    public List<ItemPostVO> search(SearchVO vo);
    public List<ItemPostVO> selectByWriter(String writer);
    public List<ItemPostVO> selectByBuyer(String buyer); //구매내역 조회
    public void addChatCnt(int postId);
    public void minusChatCnt(int postId);
    public int delete(ItemPostVO vo);
    public int updateComplete(ItemPostVO vo);
    
    public List<TradeVO> selectTradeById(String id); //거래후기 조회
}
