# adapter-bank-payout-spring-boot-starter

For new payout:

1. implement RemoteClient
2. implement ResultProcessor and config ChainProcessor:

    ```
    @Bean
    @Autowired
    public ResultProcessor<Response, ProcessResult> responseProcessorChain(ErrorMapping errorMapping) {
        SuccessProcessor successProcessor = new SuccessProcessor(null);
        return new ErrorProcessor(errorMapping, successProcessor);
    }
    ```
3. implement WithdrawalConverter
4. implement GenericServlet for woody:

    ```
    @WebServlet("/adapter/{app-path}/payout")
    public class PayoutServlet extends GenericServlet {
    
        @Autowired
        private AdapterSrv.Iface payoutAdapterServiceLogDecorator;
    
        private Servlet servlet;
    
        @Override
        public void init(ServletConfig config) throws ServletException {
            super.init(config);
            servlet = new THServiceBuilder().build(AdapterSrv.Iface.class, payoutAdapterServiceLogDecorator);
        }
    
        @Override
        public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
            servlet.service(request, response);
        }
        
    }
    ```