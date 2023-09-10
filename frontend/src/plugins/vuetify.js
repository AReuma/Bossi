import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
            light: {
                BACK_GROUND_COLOR: 'rgba(220,220,220,0.72)',
                PINK: '#fdcbcc',
                DEEP_PINK: '#fc9899',
                FONT_COLOR: '#3d4148',
                FONT_COLOR2: '#434f58',
                BACK_PINK1: 'rgba(252,152,153,0.8)',
                LINE_COLOR: 'rgba(67,79,88,0.11)'
            },
        },
    }
});
