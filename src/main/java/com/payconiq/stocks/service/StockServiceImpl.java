package com.payconiq.stocks.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payconiq.stocks.entity.Stock;
import com.payconiq.stocks.exceptions.InvalidInputException;
import com.payconiq.stocks.model.StockDto;
import com.payconiq.stocks.repository.StockRepository;
import com.payconiq.stocks.utils.StockConverter;

@Service
public class StockServiceImpl implements StockService {

  @Autowired
  private StockRepository stockRepository;

  private Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

  @Override
  public List<StockDto> getAllStocks() {
    return stockRepository.findAll().stream().map(StockConverter::entityToDto).collect(Collectors.toList());
  }

  @Override
  public StockDto getStockById(long stockId) {
    return StockConverter.entityToDto(stockRepository.getOne(stockId));
  }

  @Override
  public Stock saveStock(StockDto stockDto) {
    if (stockRepository.findById(stockDto.getStockId()).isPresent()) {
      // If stockId is not found then only you want to save. If this implementation is not done then it will update with
      // the given stock details, which might not be business requirement.
      logger.error("Stock ID already present. Cannot save by the same ID.");
      throw new InvalidInputException("Stock id already present.");
    }
    return stockRepository.save(StockConverter.dtoToEntity(stockDto));
  }

  @Override
  public Stock updateStock(StockDto stockDto, long stockId) {
    if ((stockDto.getStockId() != stockId) || (!stockRepository.findById(stockId).isPresent())) {
      // If stockId is not present, then you do not want to get this Stock saved. This implementation would be helpful
      // then.
      logger.error("Possibility of invalid stock OR Stock ID not present to update.");
      throw new InvalidInputException("Invalid Stock id OR Stock id not present.");
    }
    return stockRepository.save(StockConverter.dtoToEntity(stockDto));
  }

  @Override
  public void deleteAllStocks() {
    stockRepository.deleteAll();
  }

  @Override
  public void deleteStockById(long stockId) {
    stockRepository.deleteById(stockId);
  }
}
