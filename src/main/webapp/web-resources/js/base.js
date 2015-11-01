/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


    function convertBytes(bytes) {
        
        if (bytes < 1024) {
            var fmt = d3.format('.0f');
            return fmt(bytes) + ' B';
        } else if (bytes < 1024 * 1024) {
            var fmt = d3.format('.0f');
            return fmt(bytes / 1024) + ' kB';
        } else if (bytes < 1024 * 1024 * 1024) {
            var fmt = d3.format('.2f');
            return fmt(bytes / 1024 / 1024) + ' MB';
        } else {
            var fmt = d3.format('.3f');
            return fmt(bytes / 1024 / 1024 / 1024) + ' GB';
        }

    }

