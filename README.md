# Схема создания бинов

    1 BeanDefinitionReader парсит xml и создал BeanDefinition
    
    2 BeanFactory берет все BeanDefinition создает из них все бины которые имплементируют BeanPostProcessor
        
        2.1 Объект создается. 
        Обработка определенная в implements BeanPostProcessor 
        Вызывается метод postProcessBeforeInitialization
        
        2.2 Вызывается init() в каком-то виде на пример @PostConstruct
        
        2.3 Делается второй проход по тем же BeanPostProcessor 
        и вызывается postProcessAfterInitialization
        В методе postProcessAfterInitialization создааются Proxy 
        (в случае если надо менять логику класса). 