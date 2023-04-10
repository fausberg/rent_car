package rentcar.rentcar.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.repository.CreditCardRepository;
import rentcar.rentcar.service.CreditCardService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreditCardController creditCardController;

    @Mock
    private CreditCardService creditCardService;

    private CreditCard creditCard;

    private List<CreditCard> creditCards;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @BeforeEach
    void setCreditCard() {
        creditCard = new CreditCard(1, "1234123412341234", "02/26", "313", 1);
        creditCards = new ArrayList<>();
        creditCards.add(creditCard);
        creditCardRepository.save(creditCard);
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void getCreditCardById() throws Exception {
        when(creditCardService.getCreditCardById(anyInt())).thenReturn(creditCard);
        this.mockMvc.perform(get("/credit-card/{id}", creditCard.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(creditCard.getId())));
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void getAllCreditCards() throws Exception {
        when(creditCardService.getAllCreditCard()).thenReturn((ArrayList<CreditCard>) creditCards);
        this.mockMvc.perform(get("/credit-card/all", creditCards))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id", is(creditCard.getId())));
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void deleteCreditCard() throws Exception {
        int id = 1;
        when(creditCardService.deleteCreditCard(id)).thenReturn(true);
        mockMvc.perform(delete("/credit-card/delete/{id}", id))
                .andExpect(status().isOk());
    }
}