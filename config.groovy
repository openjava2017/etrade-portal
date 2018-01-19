environments {
    dev {
        jdbc {
            host = '10.28.6.117'
            port = '3306'
            schema = 'etrade'
            user = 'dilipay'
            password = 'dili2014'
        }

        redis {
            host = '10.28.10.219'
            port = '6379'
        }
    }

    test {
        jdbc {
            host = '10.28.6.55'
            port = '3306'
            schema = 'icard'
            user = 'root'
            password = '123456'
        }

        redis {
            host = '10.28.10.135'
            port = '6379'
        }
    }

    release {
        jdbc {
            host = '192.168.28.93'
            port = '3306'
            schema = 'icounter'
            user = 'appAccPayor'
            password = 'yF8RaIsQj8XyXONwQWCS'
        }

        redis {
            host = 'udlredis.nong12.com'
            port = '6379'
        }
    }
}