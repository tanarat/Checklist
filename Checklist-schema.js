{
    "package": "org.silk.checklist.db",
    "prefix": "",
    "database": "silk.checklist.db",
    "tables": ["Bpartner", "Group", "Checklist", "Checklist_Item", "Item", "Sheet", "Answer","Auditor"],
    "Auditor" : {
    	"columns" : [
    		{
    		"name" : "auditor_name",
    		"type" : "text"
    		}
    	]
    },
    "Bpartner" : {
    	"columns" : [
    		{
    			"name" : "company_name",
    			"type" : "text"	
    		},
    		{
    			"name" : "address",
    			"type" : "text"
    		}
    	]
    },
    "Group" : {
    	"columns" : [
    		{
    			"name" : "group_key",
    			"type" : "text"
    		},
    		{
    			"name" : "group_name",
    			"type" : "text"
    		},
    		{
    			"name" : "parent_group_id",
    			"type" : "integer"
    		},
    		{
    			"name" : "base_group_id",
    			"type" : "integer"
    		}
    	]
    },
    "Checklist": {
        "columns": [
        {
            "name": "checklist_name",
            "type": "text"
        },
        {
            "name": "updated",
            "type": "timestamp",
            "options": "default current_timestamp"
        }
        ]
    },
    "Checklist_Item": {
        "columns": [
        {
            "name": "checklist_id",
            "type": "integer"
        },
        {
            "name": "item_id",
            "type": "integer"
        }
        ]
    },
    "Item": {
        "columns": [
        {
            "name": "item_title",
            "type": "text"
        },
        {
        	"name" : "group_id",
        	"type" : "integer"
        },
        {
        	"name" : "base_group_id",
        	"type" : "integer"
        }       
        ]
    },
    "Sheet" : {
    	"columns" : [
    	{
    		"name" : "sheet_name",
    		"type" : "text"
    	},
    	{
    		"name" : "bpartner_id",
    		"type" : "integer"
    	},
    	{
    		"name" : "check_date",
    		"type": "timestamp",
            "options": "default current_timestamp"
    	}
    	]
    },
    "Answer" : {
    	"columns" : [
    	{
    		"name" : "sheet_id",
    		"type" : "integer"
    	},
    	{
    		"name" : "item_id",
    		"type" : "integer"
    	},
    	{
    		"name" : "answer",
    		"type" : "integer"
    	}
    	]
    }
    
}