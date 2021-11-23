// import { login, logout, getUserInfo } from '@/api/customer'

const customer = {
  state: {
    customerId: -1,
    customerName: null,
    idCard: null,
    bankAccount: null,
    phoneNumber: null,
  },

  mutations: {
    SET_CUSTOMER: (state, customer) => {
      state.customerId = customer.customerId;
      state.customerName = customer.customerName;
      state.idCard = customer.idCard;
      state.bankAccount = customer.bankAccount;
      state.phoneNumber = customer.phoneNumber;
    },
    RESET_CUSTOMER: (state) => {
      state.customerId = -1;
      state.customerName = null;
      state.idCard = null;
      state.bankAccount = null;
      state.phoneNumber = null;
    }
  },

  actions:{

  }
}
