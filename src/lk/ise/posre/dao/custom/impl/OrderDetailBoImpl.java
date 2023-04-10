package lk.ise.posre.dao.custom.impl;

import lk.ise.posre.dao.DaoFactory;
import lk.ise.posre.dao.custom.OrderDetailDao;
import lk.ise.posre.dto.OrderDetailDto;
import lk.ise.posre.entity.OrderDetail;
import lk.ise.posre.enums.DaoType;

public class OrderDetailBoImpl {
    private OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    public boolean saveOrderDetail(OrderDetailDto dto) throws Exception {
        return orderDetailDao.save(new OrderDetail(dto.getCode(),dto.getOrderId(), dto.getUnitPrice(),dto.getQty()));
    }
}
