import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
            light: {
                BACK_GROUND_COLOR: '#f7f3f1',
                PINK: '#fdcbcc',
                DEEP_PINK: '#fc9899',
                FONT_COLOR: '#3d4148',
                FONT_COLOR2: '#434f58'
            },
        },
    }
});
