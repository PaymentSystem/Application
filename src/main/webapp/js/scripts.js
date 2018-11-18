//   Login page
   window.alert = function(){};
        var defaultCSS = document.getElementById('bootstrap-css');
        function changeCSS(css){
            if(css) $('head > link').filter(':first')
            .replaceWith('<link rel="stylesheet" href="'+ css +'" type="text/css" />');
            else $('head > link').filter(':first').replaceWith(defaultCSS);
        }
        $( document ).ready(function() {
          var iframe_height = parseInt($('html').height());
          window.parent.postMessage( iframe_height, 'https://bootsnipp.com');
        });




        	$(function() {

            $('#login-form-link').click(function(e) {
        		$("#login-form").delay(100).fadeIn(100);
         		$("#register-form").fadeOut(100);
        		$('#register-form-link').removeClass('active');
        		$(this).addClass('active');
        		e.preventDefault();
        	});
        	$('#register-form-link').click(function(e) {
        		$("#register-form").delay(100).fadeIn(100);
         		$("#login-form").fadeOut(100);
        		$('#login-form-link').removeClass('active');
        		$(this).addClass('active');
        		e.preventDefault();
        	});

        });
//End Login page

// Owl-carousel script
jQuery(document).ready(function ($) {
    $("#owl-example").owlCarousel({
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        pagination: true,
        loop: true,
        margin: 20, //Отступ от картино если выводите больше 1
        nav: false, //Отключил навигацию
        autoplay: true, //Автозапуск слайдера
        smartSpeed: 100, //Время движения слайда
        autoplayTimeout: 25000, //Время смены слайда
        responsive: { //Адаптация в зависимости от разрешения экрана
            0: {
                items: 1
            },
            600: {
                items: 1
            },
            1000: {
                items: 1
            }
        }
    });
});

