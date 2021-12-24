package com.funicorn.cloud.chart.center.handler.impl;

import com.funicorn.cloud.chart.center.dto.ChartDataDTO;
import com.funicorn.cloud.chart.center.dto.HandlerDTO;
import com.funicorn.cloud.chart.center.handler.ChartHandler;
import org.springframework.stereotype.Service;

/**
 * @author Aimee
 * @date 2021/9/10 9:06
 */

@Service
public class BasicDashBoardHandler implements ChartHandler {

    @Override
    public ChartDataDTO invoke(HandlerDTO handlerDTO) {
        return null;
    }
}
