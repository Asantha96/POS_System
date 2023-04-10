package lk.ise.posre.bo.custom;

import lk.ise.posre.dao.custom.OrderDetailDao;
import lk.ise.posre.dto.OrderDetailDto;

public interface OrderDetailBo {
    public boolean saveOrderDetail(OrderDetailDto dto);
}
